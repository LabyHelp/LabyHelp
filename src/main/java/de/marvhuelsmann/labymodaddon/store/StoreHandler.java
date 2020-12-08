package de.marvhuelsmann.labymodaddon.store;

public class StoreHandler {

    private final FileDownloader fileDownloader = new FileDownloader();
    private final StoreSettings storeSettings = new StoreSettings();

    public FileDownloader getFileDownloader() {
        return fileDownloader;
    }

    public StoreSettings getStoreSettings() {
        return storeSettings;
    }
}
