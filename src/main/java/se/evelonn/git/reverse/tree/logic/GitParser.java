package se.evelonn.git.reverse.tree.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import se.evelonn.git.reverse.tree.logic.domain.Branch;
import se.evelonn.git.reverse.tree.logic.domain.Commit;
import se.evelonn.git.reverse.tree.logic.domain.GitReverseTree;

public class GitParser {

	public static GitReverseTree parse(Path path) throws IOException, GitAPIException {

		Git git = Git.open(path.toFile());

		List<Ref> refBranches = git.branchList().call();

		GitReverseTree tree = GitReverseTree.builder()
				.branches(
						refBranches.stream()
								.map(rb -> Branch.builder().name(rb.getName().replace("refs/heads/", ""))
										.commits(getCommitsForBranch(rb, git)).build())
								.collect(Collectors.toList()))
				.build();

		return tree;
	}

	private static List<Commit> getCommitsForBranch(Ref refBranch, Git git) {
		List<Commit> commits = new ArrayList<>();

		try {
			Iterable<RevCommit> revCommits = git.log().add(git.getRepository().resolve(refBranch.getName())).call();
			for (RevCommit revCommit : revCommits) {
				PersonIdent authorIdent = revCommit.getAuthorIdent();
				Commit commit = Commit.builder().id(revCommit.getId().name())
						.shortId(revCommit.getId().abbreviate(6).name()).commitTime(authorIdent.getWhen())
						.message(revCommit.getShortMessage()).authorName(authorIdent.getName())
						.authorEmail(authorIdent.getEmailAddress()).build();
				commits.add(commit);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch commits for branch: " + refBranch.getName(), e);
		}

		return commits;
	}
}