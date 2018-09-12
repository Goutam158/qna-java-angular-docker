import { UserModel } from './user.model';
import { TopicModel } from './topic.model';
import { CommentModel } from './comment.model';


export class QuestionModel {
    id : number;
	description : string;
	createdOn : number;
	comments : CommentModel[];
    createdBy : UserModel;
    topic : TopicModel;
}