package com.example.demo.badge;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.logging.ErrorManager;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;

    @Value("${badge.upload.directory}")
    private String directoryPath;

    @Autowired
    public BadgeService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    public List<Badge> getBadges() {
        return badgeRepository.findAll();
    }

    // Get the file path from the configuration file using @Value annotation

//    public byte[] addNewBadge(MultipartFile image, Badge badge) {
//        validateBadgeFields(badge);
//        checkBadgeByCnie(badge.getCnie());
//        validateImage(image);
//        String imageName = generateFilename(badge.getCnie(), image.getOriginalFilename());
//        BufferedImage badgeImage = loadImage(directoryPath + "\\emp.png");
//        badge.setPath_photo(imageName);
//        Badge badge2 = badgeRepository.save(badge);
//        BufferedImage mergedImage = mergeBadgeInfo(badgeImage, badge2, image);
//
//        // Convert the merged BufferedImage to a byte array
//        byte[] mergedImageBytes = convertBufferedImageToBytes(mergedImage);
//
//        saveImage(image, imageName);
//        saveImage(mergedImage, directoryPath + "\\" + badge.getCnie());
//
//        return mergedImageBytes;
//    }

        public ResponseEntity<AddBadgeResponse> addNewBadge(MultipartFile image, Badge badge) {
            AddBadgeResponse response = new AddBadgeResponse();
            try {
                validateBadgeFields(badge);
                checkBadgeByCnie(badge.getCnie());
                validateImage(image);
                String imageName = generateFilename(badge.getCnie(), image.getOriginalFilename());
                BufferedImage badgeImage = loadImage(directoryPath + "\\emp.png");
                badge.setPath_photo(imageName);
                Badge badge2 = badgeRepository.save(badge);
                BufferedImage mergedImage = mergeBadgeInfo(badgeImage, badge2, image);
                byte[] mergedImageBytes = convertBufferedImageToBytes(mergedImage);
                saveImage(image, imageName);
                saveImage(mergedImage, directoryPath + "\\" + badge.getCnie());

                response.setSuccess(true);
                response.setImageData(mergedImageBytes);
            } catch (IllegalStateException e) {
                response.setSuccess(false);
                response.setErrorMessage(e.getMessage());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    private byte[] convertBufferedImageToBytes(BufferedImage bufferedImage) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            // Handle the exception or rethrow it as needed
            e.printStackTrace();
            return null;
        }
    }
    //////////////////////////////////////////
    private static BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Merge the badge information onto the badge image
    private static BufferedImage mergeBadgeInfo(BufferedImage badgeImage, Badge badge, MultipartFile userUploadedImage) {
        // Load the user-uploaded image
        BufferedImage uploadedImage;
        try {
            uploadedImage = ImageIO.read(userUploadedImage.getInputStream());
            // Set the desired dimensions for the resized image
            int newWidth = 244; // Replace this with your desired width
            int newHeight = 318; // Replace this with your desired height
            uploadedImage = resizeImage(uploadedImage, newWidth, newHeight);
        } catch (IOException e) {
            e.printStackTrace();
            return badgeImage; // If unable to read user-uploaded image, return the original badge image
        }

        Graphics2D graphics = badgeImage.createGraphics();
        graphics.drawImage(badgeImage, 0, 0, null);
        // Set the font and color for the badge information
        //organizme
        String organizme = badge.getOrganisme();
        Font font = new Font("Arial", Font.BOLD, dynamicFontSize(organizme,32));
        Color textColor = Color.BLACK;
        graphics.setFont(font);
        graphics.setColor(textColor);
        int x = 615; // Set the X-coordinate for the text
        int y = 332; // Set the Y-coordinate for the text
        graphics.drawString(organizme, x, y);
        //fonction
        String fonction = badge.getFonction();
        Font font4 = new Font("Arial", Font.BOLD, dynamicFontSize(fonction,32));
        graphics.setFont(font4);
        int x1 = 580; // Set the X-coordinate for the text
        int y1 = 380; // Set the Y-coordinate for the text
        graphics.drawString(fonction , x1, y1);
        //id
        int x2 = 472; // Set the X-coordinate for the text
        int y2 = 426; // Set the Y-coordinate for the text
        graphics.drawString(badge.getId().toString() , x2, y2);
        //cnie
        String cnie = badge.getCnie().toUpperCase();
        Font font3 = new Font("Arial", Font.BOLD, dynamicFontSize(cnie,32));
        graphics.setFont(font3);
        Color textColor1 = Color.white;
        graphics.setColor(textColor1);
        int x3 = 103; // Set the X-coordinate for the text
        int y3 = 456; // Set the Y-coordinate for the text
        graphics.drawString(cnie , x3, y3);
        //status
        String status = badge.getStatus().toString();
        Font font5 = new Font("Arial", Font.BOLD, dynamicFontSize(status,32));
        graphics.setFont(font5);
        graphics.setColor(textColor);
        int x5 = 493; // Set the X-coordinate for the text
        int y5 = 610; // Set the Y-coordinate for the text
        graphics.drawString("valable jusqu'au: "+status , x5, y5);
        //nom et prenom
        String fullName = badge.getNom().toUpperCase() + " " + badge.getPrenom().toUpperCase();
        Font font2 = new Font("Arial", Font.BOLD, dynamicFontSize(fullName,58));
        Color textColor2 = Color.BLACK;
        graphics.setFont(font2);
        graphics.setColor(textColor2);
        int x4 = 465; // Set the X-coordinate for the text
        int y4 = 226; // Set the Y-coordinate for the text
        graphics.drawString(fullName, x4, y4);
        // add cercle

        List<String> zone_acces = badge.getZone_acces();
        int posX = 0;
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("chestnut", new Color(139, 69, 19));
        colorMap.put("blue", Color.BLUE);
        colorMap.put("green", Color.GREEN);

        for (int i = 0; i < zone_acces.size(); i++) {
            String element = zone_acces.get(i);
            Color circleColor = colorMap.get(element.toLowerCase());
            // Calculate the radius of the circle based on the image width and height
            int circleRadius = 50;
            // Draw the circle on the image
            graphics.setColor(circleColor); // Set the color of the circle (you can change this)
            graphics.fillOval(507+posX , 457, circleRadius * 2, circleRadius * 2);
            posX = posX + 132;
        }



        //add user photo
        graphics.drawImage(uploadedImage, 63, 98, null);
        graphics.dispose(); // Release resources

        return badgeImage;
    }
    private static int dynamicFontSize(String s, int maxFontSize) {

        return Math.min(maxFontSize, 700 / s.length());
    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics.dispose();
        return resizedImage;
    }
    // Save the merged image to a file
    private static void saveImage(BufferedImage image, String outputPath) {
        try {
            File outputImage = new File(outputPath+"-badge.png");
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //////////////////////////////////////////
    private void validateBadgeFields(Badge badge) {
        if (badge.getNom() == null || badge.getPrenom() == null || badge.getOrganisme() == null
                || badge.getCnie() == null || badge.getFonction() == null || badge.getDate_depot_dossier() == null
                || badge.getDate_envoi_dai() == null || badge.getDate_retour_dai() == null
                || badge.getStatus() == null || badge.getZone_acces() == null) {
            throw new IllegalStateException("All fields in the Badge object must be provided.");
        }
    }

    private void checkBadgeByCnie(String cnie) {
        Optional<Badge> badgeByCnie = badgeRepository.findFirstByCnie(cnie);
        if (badgeByCnie.isPresent()) {
            throw new IllegalStateException("cnie taken");
        }
    }

    private void validateImage(MultipartFile image) {

        long maxSizeInBytes = 1 * 1024 * 1024; // 10 MB
        String[] allowedFileTypes = { "image/jpeg", "image/png" }; // Add other allowed types as needed

        if (image.getSize() > maxSizeInBytes) {
            throw new IllegalStateException("File size exceeds the limit (1MB).");
        }

        if (!Arrays.asList(allowedFileTypes).contains(image.getContentType())) {
            throw new IllegalStateException("Only JPEG and PNG images are allowed.");
        }
    }

    private String generateFilename(String cnie, String originalFilename) {
        String fileExtension = "";

        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex >= 0 && lastDotIndex < originalFilename.length() - 1) {
            fileExtension = originalFilename.substring(lastDotIndex + 1);
        }

        return cnie + "." + fileExtension;
    }

    private void saveImage(MultipartFile image, String imageName) {
        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directoryPath, imageName);
            image.transferTo(file);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload the image.");
        }
    }
    public void deleteBadge(Long badgeId) {
        boolean exist = badgeRepository.existsById(badgeId);
        if (!exist) {
            throw new IllegalStateException(
                    "badge with id " + badgeId + " does not exists");
        }
        badgeRepository.deleteById(badgeId);

    }

    @Transactional
    public Badge updateBadge(Long badgeId, Badge b) {

        Badge badge = badgeRepository.findById(badgeId).orElseThrow(() -> new IllegalStateException(
                "badge with id " + badgeId + " does not exits "));

        Optional<Badge> badgeByCnie = badgeRepository.findFirstByCnie(badge.getCnie());
        if (badgeByCnie.isPresent()){
            throw new IllegalStateException("cnie taken");
        }

        if (b.getNom() != null && !b.getNom().isEmpty() && !Objects.equals(badge.getNom(), b.getNom())) {
            badge.setNom(b.getNom());
        }

        if (b.getPrenom() != null && !b.getPrenom().isEmpty() && !Objects.equals(badge.getPrenom(), b.getPrenom())) {
            badge.setPrenom(b.getPrenom());
        }

        if (b.getOrganisme() != null && !b.getOrganisme().isEmpty() && !Objects.equals(badge.getOrganisme(), b.getOrganisme())) {
            badge.setOrganisme(b.getOrganisme());
        }

        if (b.getCnie() != null && !b.getCnie().isEmpty() && !Objects.equals(badge.getCnie(), b.getCnie())) {
            badge.setCnie(b.getCnie());
        }

        if (b.getFonction() != null && !b.getFonction().isEmpty() && !Objects.equals(badge.getFonction(), b.getFonction())) {
            badge.setFonction(b.getFonction());
        }

        if (b.getPath_photo() != null && !b.getPath_photo().isEmpty() && !Objects.equals(badge.getPath_photo(), b.getPath_photo())) {
            badge.setPath_photo(b.getPath_photo());
        }

        if (b.getDate_depot_dossier() != null && !Objects.equals(badge.getDate_depot_dossier(), b.getDate_depot_dossier())) {
            badge.setDate_depot_dossier(b.getDate_depot_dossier());
        }

        if (b.getDate_envoi_dai() != null && !Objects.equals(badge.getDate_envoi_dai(), b.getDate_envoi_dai())) {
            badge.setDate_envoi_dai(b.getDate_envoi_dai());
        }

        if (b.getDate_retour_dai() != null && !Objects.equals(badge.getDate_retour_dai(), b.getDate_retour_dai())) {
            badge.setDate_retour_dai(b.getDate_retour_dai());
        }

        if (b.getZone_acces() != null && !b.getZone_acces().isEmpty() && !Objects.equals(badge.getZone_acces(), b.getZone_acces())) {
            badge.setZone_acces(b.getZone_acces());
        }

        if (b.getStatus() != null  && !Objects.equals(badge.getStatus(), b.getStatus())) {
            badge.setStatus(b.getStatus());
        }

        return badgeRepository.save(badge);
    }
}
