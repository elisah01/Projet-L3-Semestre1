<?php

namespace App\Service;

interface StatsService
{
    /**
     * Statistiques du dashboard gestionnaire
     */
    public function getDashboardStats(): array;
}
