package com.openclassrooms.mareu.di;

import com.openclassrooms.mareu.service.ListApiService;

/**
 * Dependency injector to get instance of service
 */
public class DI {

    private static final ListApiService service = new ListApiService();

    /**
     * Get an instance of @{@link ListApiService}
     * @return : ListAPiService
     */
    public static ListApiService getListApiService() {

        return service;
    }
}
