<?php

namespace App\Repository;

use App\Controller\Admin\CommandeController;
use App\DTO\Filter\CommandeFilterDTO;
use Doctrine\DBAL\Connection;
use Doctrine\DBAL\ParameterType;

class CommandeRepository
{
    public function __construct(
        private Connection $connection
    ) {}

    public function filter(CommandeFilterDTO $filter, int $page, int $limit): array
    {
        $offset = ($page - 1) * $limit;
        $sql = "
            SELECT c.*, cl.nom, cl.prenom, cl.telephone
            FROM commande c
            JOIN client cl ON cl.id = c.client_id
            WHERE 1=1
        ";

        $params = [];

        if ($filter->etat) {
            $sql .= " AND c.etat = :etat";
            $params['etat'] = $filter->etat;
        }

        if ($filter->date) {
            $sql .= " AND DATE(c.date_commande) = :date";
            $params['date'] = $filter->date;
        }

        if ($filter->client) {
            $sql .= " AND cl.telephone LIKE :client";
            $params['client'] = '%' . $filter->client . '%';
        }

        $sql .= " ORDER BY c.date_commande DESC LIMIT :limit OFFSET :offset";
    
        $params['limit'] = $limit;
        $params['offset'] = $offset;

        return $this->connection->fetchAllAssociative($sql, $params, [
        'limit' => ParameterType::INTEGER,
        'offset' => ParameterType::INTEGER
    ]);
    }
    public function countFilter(CommandeFilterDTO $filter): int
    {
    $sql = "SELECT COUNT(*) FROM commande c JOIN client cl ON cl.id = c.client_id WHERE 1=1";
    $params = [];
    if ($filter->etat) { $sql .= " AND c.etat = :etat"; $params['etat'] = $filter->etat; }
        if ($filter->etat) {
                    $sql .= " AND c.etat = :etat";
                    $params['etat'] = $filter->etat;
                }

                if ($filter->date) {
                    $sql .= " AND DATE(c.date_commande) = :date";
                    $params['date'] = $filter->date;
                }

                if ($filter->client) {
                    $sql .= " AND cl.telephone LIKE :client";
                    $params['client'] = '%' . $filter->client . '%';
                }
    return (int) $this->connection->fetchOne($sql, $params);
    }

    public function updateEtat(int $id, string $etat): void
    {
        $this->connection->executeStatement(
            "UPDATE commande SET etat = :etat WHERE id = :id",
            ['etat' => $etat, 'id' => $id]
        );
    }

    public function findDetails(int $id): array
    {
        return $this->connection->fetchAssociative(
            "SELECT * FROM commande WHERE id = :id",
            ['id' => $id]
        );
    }

    public function findRecent(int $limit = 5): array
    {
        return $this->connection->fetchAllAssociative("
            SELECT c.*, cl.nom, cl.prenom, 
            (SELECT COALESCE(SUM(quantite), 0) FROM commande_burger cb WHERE cb.commande_id = c.id) as total_burgers
            FROM commande c 
            JOIN client cl ON cl.id = c.client_id 
            ORDER BY c.date_commande DESC 
            LIMIT :limit", 
            ['limit' => $limit],
            ['limit' => ParameterType::INTEGER]   
         );
    }


    public function countByEtatToday(string $etat): int
    {
        return (int) $this->connection->fetchOne(
            "SELECT COUNT(*) FROM commande
             WHERE etat = :etat
             AND DATE(date_commande) = CURRENT_DATE",
            ['etat' => $etat]
        );
    }

    public function sumRecetteToday(): float
    {
        return (float) $this->connection->fetchOne(
            "SELECT COALESCE(SUM(total),0)
             FROM commande
             WHERE etat = 'VALIDE'
             AND DATE(date_commande) = CURRENT_DATE"
        );
    }
}
