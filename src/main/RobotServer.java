package main;

import Control.App_Control;
import Windows.Home;
import javax.swing.JFrame;
import View.Log_View;

public class RobotServer {

    public static void main(String[] args) {
        try {
            JFrame janela = new Home();
            janela.setVisible(true);

            Log_View.render("Servidor inciado!");

            App_Control app = new App_Control();
            
            while(1==1){
                app.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Finaliza Programa caso der erro
        System.exit(0);
    }

}
