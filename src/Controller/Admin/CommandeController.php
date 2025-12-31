<?php

namespace App\Controller\Admin;

use App\DTO\Filter\CommandeFilterDTO;
use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;


#[Route('/admin/commande')]
class CommandeController extends AbstractController
{
    #[Route('', name: 'admin_commande_index')]
    public function index(Request $request, CommandeRepository $repo): Response {
    $filter = new CommandeFilterDTO($request);    
    $limit = 5;
    $page = $request->query->getInt('page', 1);
    $total = $repo->countFilter($filter);
    $pagesCount = ceil($total / $limit);

    return $this->render('admin/commande/index.html.twig', [
        'commandes' => $repo->filter($filter, $page, $limit),
        'currentPage' => $page,
        'pagesCount' => $pagesCount,
        'total' => $total
    ]);
}

    #[Route('/{id}', name: 'admin_commande_detail')]
    public function detail(
        int $id,
        CommandeRepository $commandeRepository
    ): Response {
        return $this->render('admin/commande/detail.html.twig', [
            'commande' => $commandeRepository->findDetails($id)
        ]);
    }

    #[Route('/{id}/annuler', name: 'admin_commande_annuler')]
    public function annuler(
        int $id,
        CommandeRepository $commandeRepository
    ): Response {
        $commandeRepository->updateEtat($id, 'ANNULEE');

        return $this->redirectToRoute('admin_commande_index');
    }

    #[Route('/{id}/terminer', name: 'admin_commande_terminer')]
    public function terminer(
        int $id,
        CommandeRepository $commandeRepository
    ): Response {
        $commandeRepository->updateEtat($id, 'TERMINE');

        return $this->redirectToRoute('admin_commande_index');
    }
}
