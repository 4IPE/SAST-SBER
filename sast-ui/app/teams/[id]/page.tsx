'use client'

import { useState } from 'react'
import { useParams } from 'next/navigation'
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"

const testTeamMembers = [
  { id: 1, name: "John Doe", role: "Frontend Developer", avatar: "/avatars/john-doe.jpg" },
  { id: 2, name: "Jane Smith", role: "Backend Engineer", avatar: "/avatars/jane-smith.jpg" },
  { id: 3, name: "Mike Johnson", role: "UI/UX Designer", avatar: "/avatars/mike-johnson.jpg" },
  { id: 4, name: "Emily Brown", role: "QA Engineer", avatar: "/avatars/emily-brown.jpg" },
]

export default function TeamDetails() {
  const params = useParams()
  const [members] = useState(testTeamMembers)

  return (
    <div className="container mx-auto px-4 py-8 animate-fade-in">
      <h1 className="text-4xl font-bold mb-8 bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
        Team: {params.id}
      </h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {members.map((member) => (
          <Card key={member.id} className="bg-card hover:bg-card-hover transition-all duration-300 transform hover:-translate-y-1">
            <CardHeader className="flex flex-row items-center space-y-0 pb-2">
              <Avatar className="h-10 w-10 mr-4">
                <AvatarImage src={member.avatar} alt={member.name} />
                <AvatarFallback>{member.name.split(' ').map(n => n[0]).join('')}</AvatarFallback>
              </Avatar>
              <CardTitle className="text-xl font-bold">{member.name}</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-sm text-muted-foreground">{member.role}</p>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  )
}

