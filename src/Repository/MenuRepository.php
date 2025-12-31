<?php

namespace App\Repository;

use Doctrine\DBAL\Connection;

class MenuRepository
{
    public function __construct(
        private Connection $connection
    ) {}

    public function findAllActive(): array
    {
        return $this->connection->fetchAllAssociative(
            "SELECT * FROM menu WHERE archive = false ORDER BY created_at DESC"
        );
    }
}
