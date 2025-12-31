<?php

namespace App\Controller\Admin;

use App\Service\StatsService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/dashboard')]
class DashboardController extends AbstractController
{
    public function __construct(
        private StatsService $statsService
    ) {}

    #[Route('', name: 'admin_dashboard')]
    public function index(): Response
    {
        return $this->render('admin/dashboard/index.html.twig', [
            'stats' => $this->statsService->getDashboardStats()
        ]);
    }
}
