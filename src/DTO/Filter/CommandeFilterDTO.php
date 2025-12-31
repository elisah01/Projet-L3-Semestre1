<?php

namespace App\DTO\Filter;

use Symfony\Component\HttpFoundation\Request;

class CommandeFilterDTO
{
    public ?string $etat = null;
    public ?string $date = null;
    public ?string $client = null;

    public function __construct(Request $request)
    {
        $this->etat = $request->query->get('etat');
        $this->date = $request->query->get('date');
        $this->client = $request->query->get('client');
    }
}
