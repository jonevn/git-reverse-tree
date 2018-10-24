package se.evelonn.git.reverse.tree.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import se.evelonn.git.reverse.tree.logic.domain.GitReverseTree;

public class GitReverseTreeModel {

	SimpleObjectProperty<GitReverseTree> modelObject = new SimpleObjectProperty<GitReverseTree>(null);

	public ObservableObjectValue<GitReverseTree> getModelObject() {
		return modelObject;
	}

	public void setModelObject(GitReverseTree tree) {
		this.modelObject.set(tree);
	}
}
