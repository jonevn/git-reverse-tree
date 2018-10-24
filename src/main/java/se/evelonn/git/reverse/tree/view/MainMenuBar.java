package se.evelonn.git.reverse.tree.view;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import se.evelonn.git.reverse.tree.controller.GitReverseTreeController;

public class MainMenuBar extends MenuBar {

	private final GitReverseTreeController controller;

	public MainMenuBar(Stage stage, GitReverseTreeController controller) {
		this.controller = controller;

		getMenus().add(getFileMenu(stage));
		getMenus().add(getViewMenu());
		getMenus().add(getAboutMenu());
	}

	private Menu getFileMenu(Stage stage) {
		Menu menu = new Menu("File");

		MenuItem openMenuItem = new MenuItem("Open..");

		openMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setTitle("Select GIT repository");
				File gitDirectory = directoryChooser.showDialog(stage);
				controller.load(gitDirectory);
			}
		});

		MenuItem exitMenuItem = new MenuItem("Exit");

		exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		menu.getItems().addAll(openMenuItem, new SeparatorMenuItem(), exitMenuItem);
		return menu;
	}

	private Menu getViewMenu() {
		Menu menu = new Menu("View");

		return menu;
	}

	private Menu getAboutMenu() {
		Menu menu = new Menu("About");

		return menu;
	}
}
