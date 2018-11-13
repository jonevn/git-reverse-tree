package com.github.jonevn.git.reverse.tree.logic.domain;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GitReverseTree {

	private List<Branch> branches;

	public Optional<Branch> getMasterBranch() {
		return branches.stream().filter(b -> b.getName().equals("master")).findFirst();
	}

	public Optional<Branch> getBranchWithEarliestCommit() {
		return branches.stream().sorted().findFirst();
	}

	public void restructure() {
		Branch startBranch = getMasterBranch().orElseGet(() -> getBranchWithEarliestCommit().orElse(null));
		if (startBranch == null) {
			return;
		}

	}
}
