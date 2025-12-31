<?php

namespace App\Command;

use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Console\Attribute\AsCommand;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;

#[AsCommand(name: 'app:create-admin', description: 'Crée le premier administrateur.')]
class CreateAdminCommand extends Command
{
    public function __construct(
        private EntityManagerInterface $entityManager,
        private UserPasswordHasherInterface $passwordHasher
    ) {
        parent::__construct();
    }

    protected function execute(InputInterface $input, OutputInterface $output): int
    {
        $user = new User();
        $user->setNom('Admin');
        $user->setPrenom('System');
        $user->setTelephone('770000000');
        $user->setEmail('admin@brasilburger.com');
        $user->setRoles(['ROLE_ADMIN']);
        
        // Hachage du mot de passe
        $hashedPassword = $this->passwordHasher->hashPassword($user, 'admin1234');
        $user->setPassword($hashedPassword);

        $this->entityManager->persist($user);
        $this->entityManager->flush();

        $output->writeln('Utilisateur Admin créé avec succès !');
        return Command::SUCCESS;
    }
}