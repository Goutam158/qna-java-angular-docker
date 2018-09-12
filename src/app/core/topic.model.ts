import { UserModel } from './user.model';
import { QuestionModel } from './question.model';

export class TopicModel {
    id : number;
	name : string;
	description : string;
	createdOn : number;
	questions : QuestionModel[];
	createdBy : UserModel;
}