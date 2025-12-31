<?php

namespace App\Controller\Admin;

use App\DTO\Filter\PaiementFilterDTO;
use App\Repository\PaiementRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;



#[Route('/admin/paiement')]
class PaiementController extends AbstractController
{
    #[Route('', name: 'admin_paiement_index')]
    public function index(
        Request $request,
        PaiementRepository $paiementRepository
    ): Response {
        $filter = new PaiementFilterDTO($request);

        return $this->render('admin/paiement/index.html.twig', [
            'paiements' => $paiementRepository->filter($filter)
        ]);
    }
}
