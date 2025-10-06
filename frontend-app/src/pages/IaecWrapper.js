import React from 'react';
import { useParams } from 'react-router-dom';
import IaecModule from '../modules/iaec/IaecModule';

export default function IaecWrapper() {
  const { pieceId } = useParams();
  return <IaecModule pieceId={pieceId} />;
}
