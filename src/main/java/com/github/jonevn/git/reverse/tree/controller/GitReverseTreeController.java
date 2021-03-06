package com.github.jonevn.git.reverse.tree.controller;

import java.io.File;
import java.nio.file.Path;

import com.github.jonevn.git.reverse.tree.logic.GitParser;
import com.github.jonevn.git.reverse.tree.logic.domain.GitReverseTree;
import com.github.jonevn.git.reverse.tree.model.GitReverseTreeModel;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class GitReverseTreeController {

	private final GitReverseTreeModel model;

	public GitReverseTreeController(GitReverseTreeModel model) {
		this.model = model;
	}

	public void load(File gitRepo) {
		Path gitDirectory = gitRepo.toPath().resolve(".git");

		if (!(gitDirectory.toFile().exists() && gitDirectory.toFile().isDirectory())) {
			Alert alert = new Alert(AlertType.ERROR, gitRepo.getAbsolutePath() + " is not a git repo", ButtonType.OK);
			alert.show();
			return;
		}

		GitReverseTree tree;
		try {
			tree = GitParser.parse(gitRepo.toPath());
		} catch (Exception e) {
			new Alert(AlertType.ERROR, "Failed to parse git repo: " + e.getMessage(), ButtonType.OK).showAndWait();
			return;
		}
		model.setModelObject(tree);
	}

}
