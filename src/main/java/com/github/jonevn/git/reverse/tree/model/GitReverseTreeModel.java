package com.github.jonevn.git.reverse.tree.model;

import com.github.jonevn.git.reverse.tree.logic.domain.GitReverseTree;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;

public class GitReverseTreeModel {

	SimpleObjectProperty<GitReverseTree> modelObject = new SimpleObjectProperty<GitReverseTree>(null);

	public ObservableObjectValue<GitReverseTree> getModelObject() {
		return modelObject;
	}

	public void setModelObject(GitReverseTree tree) {
		this.modelObject.set(tree);
	}
}
