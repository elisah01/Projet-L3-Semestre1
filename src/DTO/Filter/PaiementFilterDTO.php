<?php

namespace App\DTO\Filter;

use Symfony\Component\HttpFoundation\Request;

class PaiementFilterDTO
{
    public ?string $mode = null;
    public ?string $date = null;

    public function __construct(Request $request)
    {
        $this->mode = $request->query->get('mode');
        $this->date = $request->query->get('date');
    }
}
