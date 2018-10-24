package se.evelonn.git.reverse.tree;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.evelonn.git.reverse.tree.controller.GitReverseTreeController;
import se.evelonn.git.reverse.tree.model.GitReverseTreeModel;
import se.evelonn.git.reverse.tree.view.GitReverseTreeView;

public class GitReverseTreeApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		GitReverseTreeModel model = new GitReverseTreeModel();
		GitReverseTreeController controller = new GitReverseTreeController(model);
		GitReverseTreeView view = new GitReverseTreeView(controller, model, primaryStage);

		Scene scene = new Scene(view.asParent(), 800, 600);

		primaryStage.setScene(scene);
		primaryStage.setTitle("git-reverse-tree");
		primaryStage.show();
	}
}