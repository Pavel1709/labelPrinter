/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labelprinter;

import javax.swing.JOptionPane;
import labelprinter.FrameForCodes;
/**
 *
 * @author pmolchanov
 */
public class EmptyFieldException extends Exception{
    EmptyFieldException() {
        JOptionPane.showMessageDialog(new FrameForCodes(), "Необходимо ввести ШК");
    }
}
