import { UserModel } from './user.model';
import { QuestionModel } from './question.model';


export class CommentModel {
    id : number;
	description : string;
	createdOn : number;
    createdBy : UserModel;
    question : QuestionModel;
}