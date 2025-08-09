package Controllers;

import Services.keyBoardService;
import Services.uiService;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BuisnessSelectorController implements Initializable {

    @FXML
    private HBox cardContainer;

    private int centerIndex = 1;
    private List<VBox> cards = new ArrayList<>();

    String[] businessNnames = {"KAS CERAMICS","TEST 1 Plan","TEST 2 Plan"};
    String[] images = {"/Images/B1.png","/Images/B2.png","/Images/B2.png"};

    public void initialize(URL location, ResourceBundle resources) {
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setPrefHeight(280);

        //List of business available



        for (int i = 0; i < businessNnames.length; i++) {
            VBox card = createCard(businessNnames[i], images[i]);
            card.setPrefWidth(300);
            cards.add(card);

        }

        //Adding all cars into HBox
        cardContainer.getChildren().addAll(cards);

        cardContainer.sceneProperty().addListener(new javafx.beans.value.ChangeListener<javafx.scene.Scene>() {
            @Override
            public void changed(javafx.beans.value.ObservableValue<? extends javafx.scene.Scene> observable,
                                javafx.scene.Scene oldScene, javafx.scene.Scene newScene) {
                if (newScene != null) {
                    newScene.setOnKeyPressed(new javafx.event.EventHandler<javafx.scene.input.KeyEvent>() {
                        @Override
                        public void handle(javafx.scene.input.KeyEvent event) {
                            switch (event.getCode()) {
                                case LEFT:
                                    centerIndex = (centerIndex - 1 + cards.size()) % cards.size();
                                    updateCardStyles();
                                    break;
                                case RIGHT:
                                    centerIndex = (centerIndex + 1) % cards.size();
                                    updateCardStyles();
                                    break;
                                case ENTER:
                                    if (businessNnames[centerIndex].equals("KAS CERAMICS")) {
                                        try {
                                            uiService.switchSceneKeyboard(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    else {
                                        uiService.giveErrorAlert("Incorrect Business type","Invalid Business","Please select a valid business type or Contact Code Solution");
                                    }
                                    System.out.println(businessNnames[centerIndex]);
                                    cardContainer.requestFocus();

                            }
                        }
                    });

                    cardContainer.requestFocus();
                }
            }
        });


    }

    //Creating card Vbox card styles
    private VBox createCard(String name, String imageUrl) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imageUrl), 200, 200, true, true));

        Text label = new Text(name);
        label.getStyleClass().add("bCardTitle");

        VBox card = new VBox(10, imageView, label);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("itemBox");

        return card;
    }

    private void updateCardStyles() {
        for (int i = 0; i < cards.size(); i++) {
            VBox card = cards.get(i);

            double targetScale;
            double targetOpacity;
            GaussianBlur targetEffect;

            if (i == centerIndex) {
                targetScale = 1.2;
                targetOpacity = 1.0;
                targetEffect = null;
            } else if (i == (centerIndex - 1 + cards.size()) % cards.size() ||
                    i == (centerIndex + 1) % cards.size()) {
                targetScale = 0.9;
                targetOpacity = 0.5;
                targetEffect = new GaussianBlur(3);
            } else {
                targetScale = 0.8;
                targetOpacity = 0.0;
                targetEffect = null;
            }

            // Smooth scale
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(400), card);
            scaleTransition.setToX(targetScale);
            scaleTransition.setToY(targetScale);

            // Smooth fade

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), card);
            fadeTransition.setToValue(targetOpacity);

            // Run animations together
            ParallelTransition pt = new ParallelTransition(scaleTransition, fadeTransition);
            pt.play();

            // Apply blur instantly (effect can't be animated directly)
            card.setEffect(targetEffect);
        }
    }


    @FXML
    void backBtnClick(MouseEvent event) {
        centerIndex = (centerIndex - 1 + cards.size()) % cards.size();
        updateCardStyles();

    }


    @FXML
    void frwdBtnClick(MouseEvent event) {
        centerIndex = (centerIndex + 1) % cards.size();
        updateCardStyles();
    }

    //Keyboard shortcuts

    public Runnable leftKeyPressed() {
        centerIndex = (centerIndex - 1 + cards.size()) % cards.size();
        updateCardStyles();
        return null;
    }

    @FXML
    void onSelectClicked(MouseEvent event) throws IOException {
        if (businessNnames[centerIndex].equals("KAS CERAMICS")) {
            uiService.switchScene(event,"/com/codesolution/cs_pos_v1/fxmls/dashboard.fxml","/Styles/mainStyle.css");
        }
        else {
            uiService.giveErrorAlert("Incorrect Business type","Invalid Business","Please select a valid business type or Contact Code Solution");
        }
        System.out.println(businessNnames[centerIndex]);
        cardContainer.requestFocus();
    }




}
