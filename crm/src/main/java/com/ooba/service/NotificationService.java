package com.ooba.service;

import com.ooba.model.NotificationMessage;

/**
 * Created by cleophas on 2018/10/21.
 */
public interface NotificationService {

    void sendMessage(NotificationMessage message);
}
