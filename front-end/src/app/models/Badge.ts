export interface Badge {
    id: number;
    nom: string;
    prenom: string;
    organisme: string;
    cnie: string;
    fonction: string;
    path_photo: string;
    date_depot_dossier: Date | undefined; // Use string or Date data type based on your requirements
    date_envoi_dai: Date | undefined; // Use string or Date data type based on your requirements
    date_retour_dai: Date | undefined; // Use string or Date data type based on your requirements
    zone_acces: string[]; // Use an array of strings
    status: Date | undefined;
  }