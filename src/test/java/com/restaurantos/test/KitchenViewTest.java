package com.restaurantos.test;

import com.restaurantos.view.KitchenQueueView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;

public class KitchenViewTest extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
            KitchenQueueView kitchenView = new KitchenQueueView();
            
            Scene scene = new Scene(kitchenView, 1280, 900);
            scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
            );
            
            stage.setTitle("Kitchen Queue View - Priority Label Test");
            stage.setScene(scene);
            stage.show();
            
            // Take screenshot after a delay
            Platform.runLater(() -> {
                try {
                    Thread.sleep(2000);
                    WritableImage image = scene.snapshot(null);
                    File file = new File("/tmp/kitchen-view-labels.png");
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    System.out.println("Screenshot saved to: " + file.getAbsolutePath());
                    Platform.exit();
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.exit();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
