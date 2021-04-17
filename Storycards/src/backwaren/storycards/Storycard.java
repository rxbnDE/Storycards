/*
    Storycards - A simple story card editor.
    
    Copyright (C) 2020 bennibacktbackwaren
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package backwaren.storycards;

/**
 * @brief Represents a story card.
 */
public class Storycard implements java.io.Serializable {
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6273318699805690547L;

	/**
	 * The story's priority.
	 */
	private String priority;
	
	/**
	 * The story's name.
	 */
	private String name;
	
	/**
	 * The estimated amount of story points.
	 */
	private int storyPoints;
	
	/**
	 * The story's description.
	 */
	private String description;
	
	/**
	 * The story's implementation risk.
	 */
	private String risk;

	/**
	 * The reestimated amount of story points after the story has been implemented.
	 */
	private int storyPointsPost;
	
	/**
	 * @brief Sets the back text of the story card.
	 * The backside of the story card.
	 */
	private String back;

	/**
	 * @brief Gets the story's priority.
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * @brief Sets the story's priority.
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * @brief Gets the story's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @brief Sets the story's name.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @brief Gets the estimated amount of story points.
	 * @return the storyPoints
	 */
	public int getStoryPoints() {
		return storyPoints;
	}

	/**
	 * @brief Sets the estimated amount of story points.
	 * @param storyPoints the storyPoints to set
	 */
	public void setStoryPoints(int storyPoints) {
		this.storyPoints = storyPoints;
	}

	/**
	 * @brief Gets the description of the story.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @brief Sets the description of the story.
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @brief Gets the story's risk.
	 * @return the risk
	 */
	public String getRisk() {
		return risk;
	}

	/**
	 * @brief Sets the story's risk.
	 * @param risk the risk to set
	 */
	public void setRisk(String risk) {
		this.risk = risk;
	}

	/**
	 * @brief Gets the reestimated story points.
	 * @return the storyPointsPost
	 */
	public int getStoryPointsPost() {
		return storyPointsPost;
	}

	/**
	 * @brief Sets the reestimated story points.
	 * @param storyPointsPost the storyPointsPost to set
	 */
	public void setStoryPointsPost(int storyPointsPost) {
		this.storyPointsPost = storyPointsPost;
	}

	/**
	 * @brief Gets the back text of the story card.
	 * @return the back
	 */
	public String getBack() {
		return back;
	}

	/**
	 * @brief Sets the back text of the story card.
	 * @param back the back to set
	 */
	public void setBack(String back) {
		this.back = back;
	}
	
	/**
	 * Creates a new instance of the Storycard class.
	 */
	public Storycard() {
		this.priority = "normal";
		this.name = "Unnamed";
		this.storyPoints = 0;
		this.description = "As a <role>\r\nI can <capability>,\r\n so that <receive benefit>.";
		this.risk = "normal";
		this.storyPointsPost = 0;
		this.back = "Assume <precondition>,\r\nif <action>,\r\n then <result>.";
	}
}
