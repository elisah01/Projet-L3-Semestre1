<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: "commande")]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue(strategy: "IDENTITY")]
    #[ORM\Column(type: "bigint")]
    private ?int $id = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: "client_id")]
    private User $client;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(name: "zone_id", nullable: true)]
    private ?Zone $zone = null;

    #[ORM\Column(type: "string")]
    private string $mode_consommation;

    #[ORM\Column(type: "string")]
    private string $etat;

    #[ORM\Column(type: "numeric", precision: 10, scale: 2, nullable: true)]
    private ?float $total = null;

    #[ORM\Column(type: "datetime")]
    private \DateTime $date_commande;

    public function getId(): ?int { return $this->id; }
    public function getClient(): User { return $this->client; }
    public function getZone(): ?Zone { return $this->zone; }
    public function getModeConsommation(): string { return $this->mode_consommation; }
    public function getEtat(): string { return $this->etat; }
    public function getTotal(): ?float { return $this->total; }
    public function getDateCommande(): \DateTime { return $this->date_commande; }
}
