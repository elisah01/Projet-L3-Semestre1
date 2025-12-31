<?php

namespace App\Repository;

use App\DTO\Filter\PaiementFilterDTO;
use Doctrine\DBAL\Connection;

class PaiementRepository
{
    public function __construct(
        private Connection $connection
    ) {}

    public function filter(PaiementFilterDTO $filter): array
    {
        $sql = "
            SELECT p.*, c.id AS commande_id
            FROM paiement p
            JOIN commande c ON c.id = p.commande_id
            WHERE 1=1
        ";

        $params = [];

        if ($filter->mode) {
            $sql .= " AND p.mode = :mode";
            $params['mode'] = $filter->mode;
        }

        if ($filter->date) {
            $sql .= " AND DATE(p.date_paiement) = :date";
            $params['date'] = $filter->date;
        }

        return $this->connection->fetchAllAssociative($sql, $params);
    }
}
