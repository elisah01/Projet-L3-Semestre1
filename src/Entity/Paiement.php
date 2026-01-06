<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: "paiement")]
class Paiement
{
    #[ORM\Id]
    #[ORM\GeneratedValue(strategy: "IDENTITY")]
    #[ORM\Column(type: "bigint")]
    private ?int $id = null;

    #[ORM\OneToOne]
    #[ORM\JoinColumn(name: "commande_id")]
    private Commande $commande;

    #[ORM\Column(type: 'decimal', precision: 10, scale: 2)]
    private string $montant;

    #[ORM\Column(type: "datetime")]
    private \DateTime $date_paiement;

    #[ORM\Column(type: "string")]
    private string $mode;

    public function getId(): ?int { return $this->id; }
    public function getCommande(): Commande { return $this->commande; }
    public function getMontant(): float { return $this->montant; }
    public function getDatePaiement(): \DateTime { return $this->date_paiement; }
    public function getMode(): string { return $this->mode; }
}