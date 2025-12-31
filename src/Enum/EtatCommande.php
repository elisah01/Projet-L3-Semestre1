<?php

namespace App\Enum;

enum EtatCommande: string
{
    case EN_COURS = 'EN_COURS';
    case VALIDE = 'VALIDE';
    case TERMINE = 'TERMINE';
    case ANNULEE = 'ANNULEE';
}