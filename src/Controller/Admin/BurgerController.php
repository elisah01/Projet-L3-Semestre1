<?php

namespace App\Controller\Admin;

use App\Repository\BurgerRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/burgers')]
class BurgerController extends AbstractController
{
    #[Route('', name: 'admin_burger_index')]
    public function index(BurgerRepository $burgerRepository): Response
    {
        return $this->render('admin/burger/index.html.twig', [
            'burgers' => $burgerRepository->findAllActive()
        ]);
    }
}
