<?php

namespace App\Repository;

use Doctrine\DBAL\Connection;

class ZoneRepository
{
    public function __construct(
        private Connection $connection
    ) {}

    public function findAll(): array
    {
        return $this->connection->fetchAllAssociative(
            "SELECT * FROM zone ORDER BY nom"
        );
    }

    public function commandesALivrer(int $zoneId): array
    {
        return $this->connection->fetchAllAssociative(
            "SELECT * FROM commande
             WHERE zone_id = :zone
             AND mode_consommation = 'LIVRAISON'
             AND etat = 'TERMINE'",
            ['zone' => $zoneId]
        );
    }
}
