<?php

namespace App\Command;

use App\Entity\Burger;
use App\Entity\Menu;
use App\Entity\Complement;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\Console\Style\SymfonyStyle;

#[AsCommand(name: 'app:fill-data', description: 'Remplit la base Neon avec des données de test cohérentes')]
class FillDataCommand extends Command
{
    public function __construct(private EntityManagerInterface $entityManager)
    {
        parent::__construct();
    }

    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $io = new SymfonyStyle($input, $output);
        $connection = $this->entityManager->getConnection();

        $burgersData = [
            ['Brasil Classic', '2500'],
            ['Cheese Explosion', '4000'],
            ['Spicy Brasil', '3500'],
            ['Chicken Samba', '4000'],
            ['Veggie Rio', '4500']
        ];

        foreach ($burgersData as $data) {
            $b = new Burger();
            $b->setNom($data[0]);
            $b->setPrix($data[1]);
            $b->setImageUrl('https://via.placeholder.com/300');
            $this->entityManager->persist($b);
        }

        $complements = [['Frites Maison', 1500], ['Coca-Cola 33cl', 800], ['Nuggets x6', 2500]];
        foreach ($complements as $data) {
            $c = new Complement();
            $this->setProperty($c, 'nom', $data[0]);
            $this->setProperty($c, 'prix', $data[1]);
            $this->setProperty($c, 'image_url', 'https://via.placeholder.com/300');
            $this->setProperty($c, 'created_at', new \DateTime());
            $this->setProperty($c, 'archive', false);
            $this->entityManager->persist($c);
        }

        $menus = [['Menu Classic', '7000'], ['Menu Deluxe', '9500'], ['Menu Veggie', '8000']];
        foreach ($menus as $data) {
            $m = new Menu();
            $this->setProperty($m, 'nom', $data[0]);
            $this->setProperty($m, 'image_url', 'https://via.placeholder.com/300');
            $this->setProperty($m, 'created_at', new \DateTime());
            $this->setProperty($m, 'archive', false);
            $this->entityManager->persist($m);
        }

        $this->entityManager->flush(); 

        $clientsData = [
            ['Fall', 'Ibrahima', '771234567'],
            ['Diop', 'Aminata', '789876543'],
            ['Sarr', 'Moussa', '701112233'],
            ['Ngom', 'Fatou', '768765432']
        ];

        $clientIds = [];
        foreach ($clientsData as $data) {
            $existingId = $connection->fetchOne("SELECT id FROM client WHERE telephone = ?", [$data[2]]);
            if ($existingId) {
                $clientIds[] = $existingId;
            } else {
                $connection->executeStatement(
                    "INSERT INTO client (nom, prenom, telephone) VALUES (?, ?, ?)",
                    [$data[0], $data[1], $data[2]]
                );
                $clientIds[] = $connection->lastInsertId();
            }
        }

        $etats = ['EN_COURS', 'VALIDE', 'ANNULEE', 'TERMINE'];
        $modes = ['SUR_PLACE', 'A_EMPORTER', 'LIVRAISON'];
        $today = new \DateTime();

        $burgerIds = $connection->fetchFirstColumn("SELECT id FROM burger LIMIT 5");

        for ($i = 0; $i < 15; $i++) {
            $clientId = $clientIds[array_rand($clientIds)];
            $etat = $etats[array_rand($etats)];
            $mode = $modes[array_rand($modes)];
            $total = rand(5000, 25000);
            $date = (clone $today)->modify("-".rand(0, 5)." days -".rand(1, 10)." hours");

            $connection->executeStatement(
                "INSERT INTO commande (client_id, total, etat, date_commande, mode_consommation) 
                 VALUES (?, ?, ?, ?, ?)",
                [$clientId, $total, $etat, $date->format('Y-m-d H:i:s'), $mode]
            );
            $commandeId = $connection->lastInsertId();

            if (!empty($burgerIds)) {
                $nbBurgersDiff = rand(1, 2);
                $randomKeys = (array) array_rand($burgerIds, $nbBurgersDiff);
                foreach ($randomKeys as $key) {
                    $connection->executeStatement(
                        "INSERT INTO commande_burger (commande_id, burger_id, quantite) VALUES (?, ?, ?)",
                        [$commandeId, $burgerIds[$key], rand(1, 3)]
                    );
                }
            }

            if ($etat === 'VALIDE' || $etat === 'TERMINE') {
                $modeP = ['WAVE', 'ORANGE_MONEY'][array_rand(['WAVE', 'ORANGE_MONEY'])];
                $connection->executeStatement(
                    "INSERT INTO paiement (commande_id, montant, mode, date_paiement) VALUES (?, ?, ?, ?)",
                    [$commandeId, $total, $modeP, $date->format('Y-m-d H:i:s')]
                );
            }
        }

        $io->success('Succès ! Les burgers, clients, commandes et paiements ont été insérés.');

        return Command::SUCCESS;
    }

    private function setProperty(object $object, string $property, mixed $value): void
    {
        $reflection = new \ReflectionProperty($object, $property);
        $reflection->setValue($object, $value);
    }
}
