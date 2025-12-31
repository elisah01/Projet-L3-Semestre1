<?php

namespace App\Service;

interface LivraisonService
{
    /**
     * Regrouper les commandes terminées par zone
     * et les affecter à une livraison
     */
    public function affecterCommandesParZone(int $zoneId): void;
}
