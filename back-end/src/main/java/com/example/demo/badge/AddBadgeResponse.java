package com.example.demo.badge;

public class AddBadgeResponse {
    private boolean success;
    private byte[] imageData;
    private String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AddBadgeResponse(boolean success, byte[] imageData, String errorMessage) {
        this.success = success;
        this.imageData = imageData;
        this.errorMessage = errorMessage;
    }

    public AddBadgeResponse() {
    }
}
