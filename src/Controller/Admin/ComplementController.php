<?php

namespace App\Controller\Admin;

use App\Repository\ComplementRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/complements')]
class ComplementController extends AbstractController
{
    #[Route('', name: 'admin_complement_index')]
    public function index(ComplementRepository $complementRepository): Response
    {
        return $this->render('admin/complement/index.html.twig', [
            'complements' => $complementRepository->findAllActive()
        ]);
    }
}
