package cycling;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * MiniCyclingPortal interface. The no-argument constructor of a class
 * implementing this interface should initialise the MiniCyclingPortal as
 * an empty platform with no initial racing teams nor races within it.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public interface MiniCyclingPortal extends Serializable {

	/**
	 * Get the races currently created in the platform.
	 * 
	 * @return An array of race IDs in the system or an empty array if none exists.
	 */
	int[] getRaceIds(); {

	/**
	 * The method creates a staged race in the platform with the given name and
	 * description.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name        Race's name.
	 * @param description Race's description (can be null).
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 * @return the unique ID of the created race.
	 * 
	 */
		// Check if the name is valid
		if (name == null || name.isEmpty() || name.length() > 30 || name.contains(" ")) {
			throws InvalidNameException;
		}

		// Check if the name already exists in the platform
		if (races.containsKey(name)) {
			throws IllegalNameException;
		}

		// Create a new race with the given name and description
		int raceId = nextRaceId++;
		Race race = new Race(raceId, name, description);
		races.put(name, race);

		// Return the unique ID of the created race
		return raceId;
	}

	/**
	 * Get the details from a race.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return Any formatted string containing the race ID, name, description, the
	 *         number of stages, and the total length (i.e., the sum of all stages'
	 *         length).
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	String viewRaceDetails(int raceId) throws IDNotRecognisedException; {

		// Check if the race exists in the platform
		if (!races.containsKey(raceId)) {
			throws IDNotRecognisedException;
		}

		// Return the details of the race
		return races.get(raceId).toString();

	}
	/**
	 * The method removes the race and all its related information, i.e., stages,
	 * checkpoints, and results.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	void removeRaceById(int raceId) throws IDNotRecognisedException; {

		// Check if the race exists in the platform
		if (!races.containsKey(raceId)) {
			throws IDNotRecognisedException;
		}

		// Remove the race from the platform
		races.remove(raceId);

		// Return the details of the race
		return;
	}

	/**
	 * The method queries the number of stages created for a race.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return The number of stages created for the race.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	int getNumberOfStages(int raceId) throws IDNotRecognisedException; {

		// Check if the race exists in the platform
		if (!races.containsKey(raceId)) {
			throws IDNotRecognisedException;
		}

		// Return the number of stages created for the race
		return races.get(raceId).getNumberOfStages();
	}

	/**
	 * Creates a new stage and adds it to the race.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId      The race which the stage will be added to.
	 * @param stageName   An identifier name for the stage.
	 * @param description A descriptive text for the stage.
	 * @param length      Stage length in kilometres.
	 * @param startTime   The date and time in which the stage will be raced. It
	 *                    cannot be null.
	 * @param type        The type of the stage. This is used to determine the
	 *                    amount of points given to the winner.
	 * @return the unique ID of the stage.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 * @throws IllegalNameException     If the name already exists in the platform.
	 * @throws InvalidNameException     If the name is null, empty, has more than 30
	 *                              	characters, or has white spaces.
	 * @throws InvalidLengthException   If the length is less than 5km.
	 */
	int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) 
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException; {

		// Check if the race exists in the platform
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException;
		}

		// Check if the race ID is valid
		if (raceId < 0) {
			throw new IDNotRecognisedException("Invalid race ID");
		}

		// Check if the stageName is not null or empty
		if (stageName == null || stageName.isEmpty()) {
			throw new IllegalNameException("Stage name cannot be null or empty");
		}

		// Check if the description is not null
		if (description == null) {
			throw new InvalidNameException("Description cannot be null");
		}

		// Check if the length is valid
		if (length < 5) {
			throw new InvalidLengthException("Stage length must be at least 5km");
		}

		// Check if the startTime is not null
		if (startTime == null) {
			throw new IllegalArgumentException("Start time cannot be null");
		}

		// Check if the type is not null
		if (type == null) {
			throw new IllegalArgumentException("Stage type cannot be null");
		}
		

		// Create a new stage with the given name and description
		int stageId = nextStageId++;
		Stage stage = new Stage(stageId, stageName, description, length, startTime, type);
		races.get(raceId).addStage(stage);
		return stageId;

	}

	/**
	 * Retrieves the list of stage IDs of a race.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param raceId The ID of the race being queried.
	 * @return An array of stage IDs ordered (from first to last) by their sequence in the
	 *         race or an empty array if none exists.
	 * @throws IDNotRecognisedException If the ID does not match to any race in the
	 *                                  system.
	 */
	int[] getRaceStages(int raceId) throws IDNotRecognisedException; {

		// Check if the race exists in the platform
		if (!races.containsKey(raceId)) {
			throws IDNotRecognisedException;
		}

		// Return the list of stage IDs in the race
		return races.get(raceId).getStageIds();
	}

	/**
	 * Gets the length of a stage in a race, in kilometres.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The stage's length.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	double getStageLength(int stageId) throws IDNotRecognisedException; {

		// Check if the stage exists in the platform
		if (!stages.containsKey(stageId)) {
			throws IDNotRecognisedException;
		}

		// Return the length of the stage
		return stages.get(stageId).getLength();
	}

	/**
	 * Removes a stage and all its related data, i.e., checkpoints and results.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being removed.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	void removeStageById(int stageId) throws IDNotRecognisedException; {

		// Check if the stage exists in the platform
		if (!stages.containsKey(stageId)) {
			throws IDNotRecognisedException;
		}

		// Remove the stage from the platform
		stages.remove(stageId);

		// Return the details of the stage
		return;
	}

	/**
	 * Adds a climb checkpoint to a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId         The ID of the stage to which the climb checkpoint is
	 *                        being added.
	 * @param location        The kilometre location where the climb finishes within
	 *                        the stage.
	 * @param type            The category of the climb - {@link CheckpointType#C4},
	 *                        {@link CheckpointType#C3}, {@link CheckpointType#C2},
	 *                        {@link CheckpointType#C1}, or {@link CheckpointType#HC}.
	 * @param averageGradient The average gradient for the climb.
	 * @param length          The length of the climb in kilometre.
	 * @return The ID of the checkpoint created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    checkpoint.
	 */
	int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException, InvalidStageTypeException; {

		// TODO: Implement the method
		return 0;

	}

	/**
	 * Adds an intermediate sprint to a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId  The ID of the stage to which the intermediate sprint checkpoint
	 *                 is being added.
	 * @param location The kilometre location where the intermediate sprint finishes
	 *                 within the stage.
	 * @return The ID of the checkpoint created.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidLocationException   If the location is out of bounds of the
	 *                                    stage length.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 * @throws InvalidStageTypeException  Time-trial stages cannot contain any
	 *                                    checkpoint.
	 */
	int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException; {

		// TODO: Implement the method
		return 0;
	}

	/**
	 * Removes a checkpoint from a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param checkpointId The ID of the checkpoint to be removed.
	 * @throws IDNotRecognisedException   If the ID does not match to any checkpoint in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
	void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException; {

		// TODO: Implement the method

	}

	/**
	 * Concludes the preparation of a stage. After conclusion, the stage's state
	 * should be "waiting for results".
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage to be concluded.
	 * @throws IDNotRecognisedException   If the ID does not match to any stage in
	 *                                    the system.
	 * @throws InvalidStageStateException If the stage is "waiting for results".
	 */
	void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException; {

		// TODO: Implement the method

	}

	/**
	 * Retrieves the list of checkpoint (mountains and sprints) IDs of a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The list of checkpoint IDs ordered (from first to last) by their location in the
	 *         stage.
	 * @throws IDNotRecognisedException If the ID does not match to any stage in the
	 *                                  system.
	 */
	int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException; {

		// TODO: Implement the method
		return null;

	}

	/**
	 * Creates a team with name and description.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param name        The identifier name of the team.
	 * @param description A description of the team.
	 * @return The ID of the created team.
	 * @throws IllegalNameException If the name already exists in the platform.
	 * @throws InvalidNameException If the name is null, empty, has more than 30
	 *                              characters, or has white spaces.
	 */
	int createTeam(String name, String description) throws IllegalNameException, InvalidNameException; {

		// TODO: Implement the method
		return 0;

	}

	/**
	 * Removes a team from the system.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamId The ID of the team to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
	void removeTeam(int teamId) throws IDNotRecognisedException; {

		// TODO: Implement the method
	}

	/**
	 * Get the list of teams' IDs in the system.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @return The list of IDs from the teams in the system. An empty list if there
	 *         are no teams in the system.
	 * 
	 */
	int[] getTeams(); {

		// TODO: Implement the method
		return null;
	}

	/**
	 * Get the riders of a team.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamId The ID of the team being queried.
	 * @return A list with riders' ID.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 */
	int[] getTeamRiders(int teamId) throws IDNotRecognisedException; {

		// TODO: Implement the method
		return null;

	}

	/**
	 * Creates a rider.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param teamID      The ID rider's team.
	 * @param name        The name of the rider.
	 * @param yearOfBirth The year of birth of the rider.
	 * @return The ID of the rider in the system.
	 * @throws IDNotRecognisedException If the ID does not match to any team in the
	 *                                  system.
	 * @throws IllegalArgumentException If the name of the rider is null or empty,
	 *                                  or the year of birth is less than 1900.
	 */
	int createRider(int teamID, String name, int yearOfBirth) throws IDNotRecognisedException, IllegalArgumentException; {

		// TODO: Implement the method
		return 0;
	}

	/**
	 * Removes a rider from the system. When a rider is removed from the platform,
	 * all of its results should be also removed. Race results must be updated.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param riderId The ID of the rider to be removed.
	 * @throws IDNotRecognisedException If the ID does not match to any rider in the
	 *                                  system.
	 */
	void removeRider(int riderId) throws IDNotRecognisedException; {

		// TODO: Implement the method
	}

	/**
	 * Record the times of a rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId     The ID of the stage the result refers to.
	 * @param riderId     The ID of the rider.
	 * @param checkpointTimes An array of times at which the rider reached each of the
	 *                    checkpoints of the stage, including the start time and the
	 *                    finish line.
	 * @throws IDNotRecognisedException    If the ID does not match to any rider or
	 *                                     stage in the system.
	 * @throws DuplicatedResultException   Thrown if the rider has already a result
	 *                                     for the stage. Each rider can have only
	 *                                     one result per stage.
	 * @throws InvalidCheckpointTimesException Thrown if the length of checkpointTimes is
	 *                                     not equal to n+2, where n is the number
	 *                                     of checkpoints in the stage; +2 represents
	 *                                     the start time and the finish time of the
	 *                                     stage.
	 * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
	 *                                     results". Results can only be added to a
	 *                                     stage while it is "waiting for results".
	 */
	void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpointTimes) 
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException; {

		// TODO: Implement the method
	}

	/**
	 * Get the times of a rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any exceptions are
	 * thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The array of times at which the rider reached each of the checkpoints
	 *         of the stage and the total elapsed time. The elapsed time is the
	 *         difference between the finish time and the start time. Return an
	 *         empty array if there is no result registered for the rider in the
	 *         stage. Assume the total elapsed time of a stage never exceeds 24h
	 *         and, therefore, can be represented by a LocalTime variable. There is
	 *         no need to check for this condition or raise any exception.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
	LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException; {

		// TODO: Implement the method
		return null;

	}

	/**
	 * For the general classification, the aggregated time is based on the adjusted
	 * elapsed time, not the real elapsed time. Adjustments are made to take into
	 * account groups of riders finishing very close together, e.g., the peloton. If
	 * a rider has a finishing time less than one second slower than the
	 * previous rider, then their adjusted elapsed time is the smallest of both. For
	 * instance, a stage with 200 riders finishing "together" (i.e., less than 1
	 * second between consecutive riders), the adjusted elapsed time of all riders
	 * should be the same as the first of all these riders, even if the real gap
	 * between the 200th and the 1st rider is much bigger than 1 second. There is no
	 * adjustments on elapsed time on time-trials.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @return The adjusted elapsed time for the rider in the stage. Return null if 
	 * 		  there is no result registered for the rider in the stage.
	 * @throws IDNotRecognisedException   If the ID does not match to any rider or
	 *                                    stage in the system.
	 */
	LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId)
			throws IDNotRecognisedException; {

		// TODO: Implement the method
		return null;
	}

	/**
	 * Removes the stage results from the rider.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage the result refers to.
	 * @param riderId The ID of the rider.
	 * @throws IDNotRecognisedException If the ID does not match to any rider or
	 *                                  stage in the system.
	 */
	void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException; {

		// TODO: Implement the method
	}

	/**
	 * Get the riders finished position in a a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return A list of riders ID sorted by their elapsed time. An empty list if
	 *         there is no result for the stage.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException; {

		// TODO: Implement the method
		return null;
	}

	/**
	 * Get the adjusted elapsed times of riders in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any exceptions are
	 * thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of adjusted elapsed times sorted by their finish
	 *         time. An empty list if there is no result for the stage. These times
	 *         should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}. Assume the total elapsed time of
	 *         in a stage never exceeds 24h and, therefore, can be represented by a
	 *         LocalTime variable. There is no need to check for this condition or
	 *         raise any exception.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException; {

		// TODO: Implement the method
		return null;
	}

	/**
	 * Get the number of points obtained by each rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of points each riders received in the stage, sorted
	 *         by their elapsed time. An empty list if there is no result for the
	 *         stage. These points should match the riders returned by
	 *         {@link #getRidegetRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException; {

		// TODO: Implement the method
		return null;
	}

	/**
	 * Get the number of mountain points obtained by each rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId The ID of the stage being queried.
	 * @return The ranked list of mountain points each riders received in the stage,
	 *         sorted by their finish time. An empty list if there is no result for
	 *         the stage. These points should match the riders returned by
	 *         {@link #getRidersRankInStage(int)}.
	 * @throws IDNotRecognisedException If the ID does not match any stage in the
	 *                                  system.
	 */
	int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException; {

		// TODO: Implement the method
		return null;
	}

	/**
	 * Method empties this MiniCyclingPortal of its contents and resets all
	 * internal counters.
	 */
	void eraseCyclingPortal(); {

		// TODO: Implement the method
	}

	/**
	 * Method saves this MiniCyclingPortal contents into a serialised file,
	 * with the filename given in the argument.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param filename Location of the file to be saved.
	 * @throws IOException If there is a problem experienced when trying to save the
	 *                     store contents to the file.
	 */
	void saveCyclingPortal(String filename) throws IOException; {

		// TODO: Implement the method
	}

	/**
	 * Method should load and replace this MiniCyclingPortal contents with the
	 * serialised contents stored in the file given in the argument.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param filename Location of the file to be loaded.
	 * @throws IOException            If there is a problem experienced when trying
	 *                                to load the store contents from the file.
	 * @throws ClassNotFoundException If required class files cannot be found when
	 *                                loading.
	 */
	void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException; {

		// TODO: Implement the method
	}

}
