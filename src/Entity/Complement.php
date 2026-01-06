<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: "complement")]
class Complement
{
    #[ORM\Id]
    #[ORM\GeneratedValue(strategy: "IDENTITY")]
    #[ORM\Column(type: "bigint")]
    private ?int $id = null;

    #[ORM\Column(length: 150)]
    private string $nom;

    #[ORM\Column(type: "decimal", precision: 10, scale: 2)]
    private string $prix;

    #[ORM\Column(type: "text", nullable: true)]
    private ?string $image_url = null;

    #[ORM\Column(type: "boolean")]
    private bool $archive = false;

    #[ORM\Column(type: "datetime")]
    private \DateTime $created_at;

    
    public function getId(): ?int { return $this->id; }
    public function getNom(): string { return $this->nom; }
    public function getPrix(): float { return $this->prix; }
    public function getImageUrl(): ?string { return $this->image_url; } 
}    