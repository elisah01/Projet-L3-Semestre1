<?php

namespace App\Service\Impl;

use App\Repository\ZoneRepository;
use Doctrine\DBAL\Connection;
use App\Service\LivraisonService;

class LivraisonServiceImpl implements LivraisonService
{
    public function __construct(
        private ZoneRepository $zoneRepository,
        private Connection $connection
    ) {}

    public function affecterCommandesParZone(int $zoneId): void
    {
        $commandes = $this->zoneRepository->commandesALivrer($zoneId);

        if (empty($commandes)) {
            return;
        }

        // Création d’une livraison
        $this->connection->executeStatement(
            "INSERT INTO livraison (zone_id) VALUES (:zone)",
            ['zone' => $zoneId]
        );

        $livraisonId = (int) $this->connection->lastInsertId();

        // Affectation des commandes
        foreach ($commandes as $commande) {
            $this->connection->executeStatement(
                "INSERT INTO livraison_commande (livraison_id, commande_id)
                 VALUES (:livraison, :commande)",
                [
                    'livraison' => $livraisonId,
                    'commande' => $commande['id']
                ]
            );
        }
    }
}
