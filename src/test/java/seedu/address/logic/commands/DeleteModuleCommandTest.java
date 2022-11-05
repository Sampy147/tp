package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.task.Task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS;
import static seedu.address.logic.commands.DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.logic.commands.DeleteTaskCommand.MESSAGE_EXAM_LINK_DROPPED;
import static seedu.address.testutil.TypicalIndexes.*;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteModuleCommand}.
 */
public class DeleteModuleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredListWithModuleUnrelatedToAnyTaskOrExam_success() {
        Module moduleToDelete = model.getFilteredModuleList()
                .get(INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM.getZeroBased());
        DeleteModuleCommand deleteModuleCommand =
                new DeleteModuleCommand(INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM);

        String expectedMessage = String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredListWithModuleUnrelatedToAnyTaskOrExam_throwsCommandException() {
        int moduleListSizePlusOne = model.getFilteredModuleList().size() + 1;
        Index outOfBoundIndex = Index.fromOneBased(moduleListSizePlusOne);
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(outOfBoundIndex);
        String expectedMessage =
                String.format(Messages.MESSAGE_INVALID_MODULE_INDEX_TOO_LARGE, moduleListSizePlusOne);

        assertCommandFailure(deleteModuleCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredListWithModuleUnrelatedToAnyTaskOrExam_success() {
        showModuleAtIndex(model, INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM);

        Module moduleToDelete =
                model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(INDEX_FIRST_MODULE);

        String expectedMessage = String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        showNoModule(expectedModel);

        assertCommandSuccess(deleteModuleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredListWithModuleUnrelatedToAnyTaskOrExam_throwsCommandException() {
        showModuleAtIndex(model, INDEX_MODULE_UNRELATED_TO_ANY_TASK_OR_EXAM);

        Index outOfBoundIndex = INDEX_SECOND_MODULE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getModuleList().size());

        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(outOfBoundIndex);

        String expectedMessage =
                String.format(Messages.MESSAGE_INVALID_MODULE_INDEX_TOO_LARGE, model.getFilteredModuleList().size() + 1);

        assertCommandFailure(deleteModuleCommand, model, expectedMessage);
    }

//    @Test
//    public void execute_linkedTaskUnfilteredList_success() {
//        Task taskToDelete = model.getFilteredTaskList().get(INDEX_LINKED_TASK.getZeroBased());
//        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(INDEX_LINKED_TASK);
//
//        String expectedMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete)
//                + MESSAGE_EXAM_LINK_DROPPED;
//
//        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        expectedModel.deleteTask(taskToDelete);
//
//        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void equals() {
//        DeleteModuleCommand deleteModuleFirstCommand = new DeleteModuleCommand(INDEX_FIRST_MODULE);
//        DeleteModuleCommand deleteModuleSecondCommand = new DeleteModuleCommand(INDEX_SECOND_MODULE);
//
//        // same object -> returns true
//        assertTrue(deleteModuleFirstCommand.equals(deleteModuleFirstCommand));
//
//        // same values -> returns true
//        DeleteModuleCommand deleteModuleFirstCommandCopy = new DeleteModuleCommand(INDEX_FIRST_MODULE);
//        assertTrue(deleteModuleFirstCommand.equals(deleteModuleFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(deleteModuleFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(deleteModuleFirstCommand.equals(null));
//
//        // different module index -> returns false
//        assertFalse(deleteModuleFirstCommand.equals(deleteModuleSecondCommand));
//    }
//
//
//
    private void showNoModule(Model model) {
        model.updateFilteredModuleList(m -> false);

        assertTrue(model.getFilteredModuleList().isEmpty());
    }
}
