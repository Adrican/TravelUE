package com.example.application.travelue;

import java.util.List;

/**
 * Created by Adri on 26/01/2017.
 */
public interface DirectionFinderListener {
        void onDirectionFinderStart();
        void onDirectionFinderSuccess(List<Route> route);
    }


