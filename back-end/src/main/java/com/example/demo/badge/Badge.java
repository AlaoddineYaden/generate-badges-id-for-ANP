package com.example.demo.badge;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table
public class Badge {

    @SequenceGenerator(
            name = "badge_sequence",
            sequenceName = "badge_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "badge_sequence"
    )
    @Id
    private Long id;
    private String nom;
    private String prenom;
    private String organisme;
    private String cnie;
    private String fonction;
    private String path_photo;
    private LocalDate date_depot_dossier;
    private LocalDate date_envoi_dai;
    private LocalDate date_retour_dai;
    @ElementCollection
    private List<String> zone_acces;
    private LocalDate status;

    public Badge() {
    }

    public Badge(Long id, String nom, String prenom, String organisme, String cnie, String fonction, String path_photo, LocalDate date_depot_dossier, LocalDate date_envoi_dai, LocalDate date_retour_dai, List<String> zone_acces, LocalDate status) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.organisme = organisme;
        this.cnie = cnie;
        this.fonction = fonction;
        this.path_photo = path_photo;
        this.date_depot_dossier = date_depot_dossier;
        this.date_envoi_dai = date_envoi_dai;
        this.date_retour_dai = date_retour_dai;
        this.zone_acces = zone_acces;
        this.status = status;
    }

    public Badge(String nom, String prenom, String organisme, String cnie, String fonction, String path_photo, LocalDate date_depot_dossier, LocalDate date_envoi_dai, LocalDate date_retour_dai, List<String> zone_acces, LocalDate status) {
        this.nom = nom;
        this.prenom = prenom;
        this.organisme = organisme;
        this.cnie = cnie;
        this.fonction = fonction;
        this.path_photo = path_photo;
        this.date_depot_dossier = date_depot_dossier;
        this.date_envoi_dai = date_envoi_dai;
        this.date_retour_dai = date_retour_dai;
        this.zone_acces = zone_acces;
        this.status = status;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getOrganisme() {
        return organisme;
    }

    public void setOrganisme(String organisme) {
        this.organisme = organisme;
    }

    public String getCnie() {
        return cnie;
    }

    public void setCnie(String cnie) {
        this.cnie = cnie;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getPath_photo() {
        return path_photo;
    }

    public void setPath_photo(String path_photo) {
        this.path_photo = path_photo;
    }

    public LocalDate getDate_depot_dossier() {
        return date_depot_dossier;
    }

    public void setDate_depot_dossier(LocalDate date_depot_dossier) {
        this.date_depot_dossier = date_depot_dossier;
    }

    public LocalDate getDate_envoi_dai() {
        return date_envoi_dai;
    }

    public void setDate_envoi_dai(LocalDate date_envoi_dai) {
        this.date_envoi_dai = date_envoi_dai;
    }

    public LocalDate getDate_retour_dai() {
        return date_retour_dai;
    }

    public void setDate_retour_dai(LocalDate date_retour_dai) {
        this.date_retour_dai = date_retour_dai;
    }

    public List<String> getZone_acces() {
        return zone_acces;
    }

    public void setZone_acces(List<String> zone_acces) {
        this.zone_acces = zone_acces;
    }

    public LocalDate getStatus() {
        return status;
    }

    public void setStatus(LocalDate status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", organisme='" + organisme + '\'' +
                ", cnie='" + cnie + '\'' +
                ", fonction='" + fonction + '\'' +
                ", path_photo='" + path_photo + '\'' +
                ", date_depot_dossier=" + date_depot_dossier +
                ", date_envoi_dai=" + date_envoi_dai +
                ", date_retour_dai=" + date_retour_dai +
                ", zone_acces='" + zone_acces + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

}
