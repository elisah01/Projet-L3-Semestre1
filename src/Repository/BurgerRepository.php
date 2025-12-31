<?php

namespace App\Repository;

use Doctrine\DBAL\Connection;

class BurgerRepository
{
    public function __construct(
        private Connection $connection
    ) {}

    public function findAllActive(): array
    {
        return $this->connection->fetchAllAssociative(
            "SELECT *
             FROM burger
             WHERE archive = false
             ORDER BY created_at DESC"
        );
    }
}
