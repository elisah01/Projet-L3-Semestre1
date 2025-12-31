<?php

namespace App\Service\Impl;

use App\Repository\CommandeRepository;
use App\Repository\StatsRepository;
use App\Service\StatsService;


class StatsServiceImpl implements StatsService
{
    public function __construct(
        private CommandeRepository $commandeRepository,
        private StatsRepository $statsRepository
    ) {}

    public function getDashboardStats(): array
    {
        return [
            'commandes_en_cours' => $this->commandeRepository->countByEtatToday('EN_COURS'),
            'commandes_validees' => $this->commandeRepository->countByEtatToday('VALIDE'),
            'commandes_annulees' => $this->commandeRepository->countByEtatToday('ANNULEE'),
            'recette_journaliere' => $this->commandeRepository->sumRecetteToday(),
            'burgers_plus_vendus' => $this->statsRepository->burgersPlusVendusDuJour(),
            'menus_plus_vendus' => $this->statsRepository->menusPlusVendusDuJour(),
            'commandes_recentes' => $this->commandeRepository->findRecent(5),
        ];
    }
}
