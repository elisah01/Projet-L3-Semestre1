<?php

namespace App\Controller\Admin;

use App\Repository\MenuRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/menu')]
class MenuController extends AbstractController
{
    #[Route('', name: 'admin_menu_index')]
    public function index(MenuRepository $menuRepository): Response
    {
        return $this->render('admin/menu/index.html.twig', [
            'menus' => $menuRepository->findAllActive()
        ]);
    }
}
