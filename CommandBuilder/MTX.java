package CommandBuilder;

import TCP.TCPSend;

/**
 *
 * @author E.Johnston
 */


public class MTX {    
        public void setVolume(int zone, int volindb)
        {
            CheckZone(zone);
            CheckVol(volindb);
            String responsearg = sendCommand("SV"+String.valueOf(zone), String.valueOf(volindb));
            try{
                if (responsearg.charAt(0) != (char)43)
                {
                    throw new Exception(TCPSend.mtxip);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    private void CheckVol(int volindb) throws IllegalArgumentException {
        if (volindb < 0 && volindb > 70) {
            throw new IllegalArgumentException("Volume out of range.");
        }
    }
    private void CheckZone(int zone) throws IllegalArgumentException {
        if (zone < 1 && zone > 8) {
            throw new IllegalArgumentException("Zone out of range.");
        }
    }
    private void CheckInput(int input1to4) throws IllegalArgumentException {
        if (input1to4 < 1 && input1to4 > 4) {
            throw new IllegalArgumentException("Input out of range.");
        }
    }
        public int getVolume(int zone)
        {
            CheckZone(zone);
            return Integer.valueOf(sendCommand("GV0"+String.valueOf(zone),""));
        }
        
        public void setInput(int zone, int source1to4)
        {
            CheckZone(zone);
            CheckInput(source1to4);
            source1to4 = source1to4 + 2;
            String responsearg = sendCommand("SR"+String.valueOf(zone), String.valueOf(source1to4));
             try {
                if (responsearg.charAt(0) != (char)43)
                {
                    throw new Exception(TCPSend.mtxip);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    
        public int getInput(int zone)
        {
            CheckZone(zone);
            return Integer.valueOf(sendCommand("GR0"+String.valueOf(zone),""))-2;
        }
        
        public void setMute(int zone, Boolean mute)
        {
            CheckZone(zone);
            int mutestate;
            if (!mute) { mutestate=0;} else {mutestate=1;}
            String responsearg = sendCommand("SM0"+String.valueOf(zone), String.valueOf(mutestate));
             try {
                if (responsearg.charAt(0) != (char)43)
                {
                    throw new Exception(TCPSend.mtxip);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        public Boolean getMute(int zone)
        {
            CheckZone(zone);
           int mutestate= Integer.valueOf(sendCommand("GM0"+String.valueOf(zone),""));
           if (mutestate==0){return false;}else {return true;}
        }
        
    private static String sendCommand(String command, String args)
    {
            //#|X001|web|SV2|40|U|
        Character linefeed = (char)10;
        String tosend = "#|X001|web|".concat(command).concat("|").concat(args).concat("|U|").concat(linefeed.toString());
        String response = "";
        response = TCPSend.toMTX(tosend); 
        String[] cmdresp = response.split("\\|");
        return cmdresp[4];
}
    }