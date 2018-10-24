package se.evelonn.git.reverse.tree.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import se.evelonn.git.reverse.tree.controller.GitReverseTreeController;
import se.evelonn.git.reverse.tree.logic.domain.Branch;
import se.evelonn.git.reverse.tree.logic.domain.Commit;
import se.evelonn.git.reverse.tree.logic.domain.GitReverseTree;
import se.evelonn.git.reverse.tree.model.GitReverseTreeModel;

public class GitReverseTreeView {

	private final GitReverseTreeController controller;
	private final GitReverseTreeModel model;

	private final VBox parent;
	private final Stage primaryStage;

	private final HBox innerView;

	public GitReverseTreeView(GitReverseTreeController controller, GitReverseTreeModel model, Stage primaryStage) {
		this.controller = controller;
		this.model = model;
		this.parent = new VBox();
		this.primaryStage = primaryStage;

		addMenuBar();

		this.innerView = new HBox();

		ScrollPane scrollPane = new ScrollPane(innerView);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		this.parent.getChildren().add(scrollPane);

		observeModel();

	}

	private void observeModel() {
		model.getModelObject().addListener(new ChangeListener<GitReverseTree>() {

			@Override
			public void changed(ObservableValue<? extends GitReverseTree> observable, GitReverseTree oldValue,
					GitReverseTree newValue) {
				updateView(newValue);
			}
		});
	}

	protected void updateView(GitReverseTree tree) {
		innerView.getChildren().clear();

		for (Branch branch : tree.getBranches()) {
			VBox vBox = new VBox();
			Label label = new Label(branch.getName());
			label.setMinWidth(200);
			label.setAlignment(Pos.CENTER);
			label.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, null)));
			vBox.getChildren().add(label);
			branch.getCommits().stream().sorted().forEach(commit -> vBox.getChildren().add(bubble(commit)));

			List<Node> fixedList = vBox.getChildren().stream()
					.collect(Collector.of(() -> new ArrayList<Node>(), (list, node) -> {
						if (list.isEmpty()) {
							list.add(node);
						} else {
							Line line = new Line();
							line.setFill(Color.BLACK);

							Node first = list.get(list.size() - 1);
							if (first instanceof StackPane) {
								StackPane stackPane = (StackPane) first;
								Circle circle = (Circle) stackPane.getChildren().stream()
										.filter(c -> c instanceof Circle).findFirst().get();
								line.startXProperty().bind(circle.centerXProperty().add(circle.translateXProperty()));
								line.startYProperty().bind(circle.centerYProperty().add(circle.translateYProperty()));
							} else {
								line.setStartX(first.getLayoutX());
								line.setStartY(first.getLayoutY());
							}

							if (node instanceof StackPane) {
								StackPane stackPane = (StackPane) node;
								Circle circle = (Circle) stackPane.getChildren().stream()
										.filter(c -> c instanceof Circle).findFirst().get();
								line.endXProperty().bind(circle.centerXProperty().add(circle.translateXProperty()));
								line.endYProperty().bind(circle.centerYProperty().add(circle.translateYProperty()));
								circle.setCenterY(circle.getCenterY() + 10 * list.size());
							} else {
								line.setEndX(node.getLayoutX());
								line.setEndY(node.getLayoutY());
							}

							line.setStrokeType(StrokeType.CENTERED);
							list.add(new StackPane(line));
							list.add(node);
						}
					}, (first, second) -> {
						first.addAll(second);
						return first;
					}, Collections::unmodifiableList));
			vBox.getChildren().clear();
			vBox.getChildren().addAll(fixedList);

			// insert line between all children
			innerView.getChildren().add(vBox);
		}
	}

	private static StackPane bubble(Commit commit) {
		StackPane stackPane = new StackPane();

		Circle circle = new Circle();
		circle.setRadius(25);
		circle.setFill(Color.RED);

		Text text = new Text(commit.getShortId());
		text.setFont(Font.font(10));
		text.setBoundsType(TextBoundsType.VISUAL);

		Tooltip tooltip = new Tooltip();
		tooltip.setText(commit.tooltipText());
		tooltip.setWrapText(true);
		tooltip.setWidth(25);
		tooltip.setTextOverrun(OverrunStyle.CENTER_WORD_ELLIPSIS);

		Tooltip.install(circle, tooltip);

		stackPane.getChildren().addAll(circle, text);
		return stackPane;
	}

	private void addMenuBar() {
		this.parent.getChildren().add(new MainMenuBar(primaryStage, controller));
	}

	public VBox asParent() {
		return parent;
	}

}
