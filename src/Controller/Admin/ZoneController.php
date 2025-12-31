<?php

namespace App\Controller\Admin;

use App\Repository\ZoneRepository;
use App\Service\LivraisonService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/zone')]
class ZoneController extends AbstractController
{
    public function __construct(
        private LivraisonService $livraisonService
    ) {}

    #[Route('', name: 'admin_zone_index')]
    public function index(ZoneRepository $zoneRepository): Response
    {
        return $this->render('admin/zone/index.html.twig', [
            'zones' => $zoneRepository->findAll()
        ]);
    }

    #[Route('/{id}/affecter', name: 'admin_zone_affecter')]
    public function affecterCommandes(int $id): Response
    {
        $this->livraisonService->affecterCommandesParZone($id);

        return $this->redirectToRoute('admin_zone_index');
    }
}
