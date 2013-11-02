/**
 * 
 */
package shared.communication;

/**
 * SUBMIT BATCH
 * 
 * Submits the indexed record field values for a batch to the Server
 * 
 * The Server should un-assign the user from the submitted batch. The Server
 * should increment the total number of records indexed by the user so that
 * the system can tell the user how many records they have indexed each time
 * they log in. (NOTE: This is the number of individual names the user has
 * indexed, not the number of batches. To simplify this calculation, when a
 * batch is submitted, give the user credit for indexing all records on the
 * batch, even if they didn�t do them all.)
 * 
 * After a batch has been submitted, the Server should allow the batch to be
 * searched by key word.
 * 
 * INPUTS
 * 
 * USER ::= String User�s name 
 * PASSWORD ::= String User�s password 
 * BATCH ::= Integer Batch ID 
 * RECORD_VALUES ::= Comma-separated list of record values
 * ordered String(,String)* by a left-to-right, top-to-bottom traversal of
 * the image
 * 
 * OUTPUTS If the operation succeeds,
 * 
 * FORMAT EXAMPLE OUTPUT ::= TRUE\n
 * 
 * If the operation fails for any reason (e.g., invalid batch ID, invalid
 * user name or password, user doesn�t own the submitted batch, wrong number
 * of values, can�t connect to the server, internal server error, etc.),
 * 
 * FORMAT EXAMPLE OUTPUT ::= FAILED\n
 *
 * @author Kevin
 *
 */
public class SubmitBatch_result extends Base_result {

	private String result_string;

	/**
	 * 
	 */
	public SubmitBatch_result() {
		// TODO Auto-generated constructor stub
	}
	
	public String getResultstring() {
		StringBuilder srb = new StringBuilder();
		if(super.getSuccess() == 1) { //success
			srb.append("TRUE\n");
		} else if(super.getSuccess() == 2) { //failed
			srb.append("FAILED\n");
		} else
			return null;
		
		result_string = srb.toString();
		return result_string;
	}
}
