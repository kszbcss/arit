/*
 * Copyright 2010-2011 Andreas Veithen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.arit.servlet.icon;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RenderedImage;

import org.springframework.beans.factory.InitializingBean;

import com.googlecode.arit.icon.imageio.ImageIO;
import com.googlecode.arit.icon.variant.GrayFilter;
import com.googlecode.arit.icon.variant.TransformationVariant;

public class Defunct extends TransformationVariant implements InitializingBean {
    private BufferedImage skull;
    
    public void afterPropertiesSet() throws Exception {
        skull = ImageIO.read(Defunct.class.getResource("defunct.gif"));
    }

    @Override
    protected RenderedImage transform(BufferedImage image) {
        GrayFilter filter = new GrayFilter();
        ImageProducer prod = new FilteredImageSource(image.getSource(), filter);
        Image grayImage = Toolkit.getDefaultToolkit().createImage(prod);
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = outImage.getGraphics();
        g.drawImage(grayImage, 0, 0, null);
        g.drawImage(skull, 0, height/2, width/2, height/2, null);
        g.dispose();
        return outImage;
    }
}
