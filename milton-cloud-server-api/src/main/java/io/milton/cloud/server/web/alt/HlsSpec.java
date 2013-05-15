/*
 * Copyright 2013 McEvoy Software Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.milton.cloud.server.web.alt;

/**
 *
 * @author brad
 */
public class HlsSpec extends Dimension {

    private int targetVideoBandwidthK;
    private int targetAudioBandwidthK;
    private int frameRate;
    
    public HlsSpec(int height, int width, int targetVideoBandwidthK, int targetAudioBandwidthK, int frameRate) {
        super(height, width);
        this.targetVideoBandwidthK = targetVideoBandwidthK;
        this.targetAudioBandwidthK = targetAudioBandwidthK;
        this.frameRate = frameRate;
    }

    /**
     * Target bandwidth in K
     * 
     * @return 
     */
    public int getTargetVideoBandwidthK() {
        return targetVideoBandwidthK;
    }

    public int getTargetAudioBandwidthK() {
        return targetAudioBandwidthK;
    }

    public int getFrameRate() {
        return frameRate;
    }
    
    
    
    
    
}
