<?php

namespace App\Repository;

use Doctrine\DBAL\Connection;

class StatsRepository
{
    public function __construct(
        private Connection $connection
    ) {}

    public function burgersPlusVendusDuJour(): array
    {
        return $this->connection->fetchAllAssociative("
            SELECT b.nom, SUM(cb.quantite) AS total
            FROM commande_burger cb
            JOIN burger b ON b.id = cb.burger_id
            JOIN commande c ON c.id = cb.commande_id
            WHERE DATE(c.date_commande) = CURRENT_DATE
            AND c.etat = 'VALIDE'
            GROUP BY b.nom
            ORDER BY total DESC
            LIMIT 5
        ");
    }

    public function menusPlusVendusDuJour(): array
    {
        return $this->connection->fetchAllAssociative("
            SELECT m.nom, SUM(cm.quantite) AS total
            FROM commande_menu cm
            JOIN menu m ON m.id = cm.menu_id
            JOIN commande c ON c.id = cm.commande_id
            WHERE DATE(c.date_commande) = CURRENT_DATE
            AND c.etat = 'VALIDE'
            GROUP BY m.nom
            ORDER BY total DESC
            LIMIT 5
        ");
    }
}
